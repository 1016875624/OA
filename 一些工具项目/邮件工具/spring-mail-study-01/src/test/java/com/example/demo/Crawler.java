package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.NamedRunnable;

public final class Crawler {
	private final OkHttpClient client;
	private final Set<HttpUrl> fetchedUrls = Collections.synchronizedSet(new LinkedHashSet<HttpUrl>());
	private final LinkedBlockingQueue<HttpUrl> queue = new LinkedBlockingQueue<>();
	private final ConcurrentHashMap<String, AtomicInteger> hostnames = new ConcurrentHashMap<>();

	public Crawler(OkHttpClient client) {
		this.client = client;
	}

	private void parallelDrainQueue(int threadCount) {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			executor.execute(new NamedRunnable("Crawler %s", i) {
				@Override
				protected void execute() {
					try {
						drainQueue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		executor.shutdown();
	}

	private void drainQueue() throws Exception {
		for (HttpUrl url; (url = queue.take()) != null;) {
			if (!fetchedUrls.add(url)) {
				continue;
			}

			Thread currentThread = Thread.currentThread();
			String originalName = currentThread.getName();
			currentThread.setName("Crawler " + url.toString());
			try {
				fetch(url);
			} catch (IOException e) {
				System.out.printf("XXX: %s %s%n", url, e);
			} finally {
				currentThread.setName(originalName);
			}
		}
	}

	public void fetch(HttpUrl url) throws IOException {
		// Skip hosts that we've visited many times.
		AtomicInteger hostnameCount = new AtomicInteger();
		AtomicInteger previous = hostnames.putIfAbsent(url.host(), hostnameCount);
		if (previous != null)
			hostnameCount = previous;
		if (hostnameCount.incrementAndGet() > 100)
			return;

		Request request = new Request.Builder().url(url).
				header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36")
				.build();
		try (Response response = client.newCall(request).execute()) {
			String responseSource = response.networkResponse() != null
					? ("(network: " + response.networkResponse().code() + " over " + response.protocol() + ")")
					: "(cache)";
			int responseCode = response.code();

			System.out.printf("%03d: %s %s%n", responseCode, url, responseSource);

			String contentType = response.header("Content-Type");
			if (responseCode != 200 || contentType == null) {
				return;
			}

			MediaType mediaType = MediaType.parse(contentType);
			if (mediaType == null || !mediaType.subtype().equalsIgnoreCase("html")) {
				return;
			}

			Document document = Jsoup.parse(response.body().string(), url.toString());
			for (Element element : document.select("a[href]")) {
				String href = element.attr("href");
				HttpUrl link = response.request().url().resolve(href);
				if (link == null)
					continue; // URL is either invalid or its scheme isn't http/https.
				queue.add(link.newBuilder().fragment(null).build());
			}
		}
	}

	/*public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: Crawler <cache dir> <root>");
			return;
		}

		int threadCount = 20;
		long cacheByteCount = 1024L * 1024L * 100L;

		Cache cache = new Cache(new File(args[0]), cacheByteCount);
		OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();

		Crawler crawler = new Crawler(client);
		crawler.queue.add(HttpUrl.get(args[1]));
		crawler.parallelDrainQueue(threadCount);
	}*/
	public static void main(String[] args) {
		int threadCount = 20;
		long cacheByteCount = 1024L * 1024L * 100L;

		Cache cache = new Cache(new File("F:/crawler/aaa/"), cacheByteCount);
		OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();

		Crawler crawler = new Crawler(client);
		crawler.queue.add(HttpUrl.get("https://ip.cn/"));
		crawler.parallelDrainQueue(threadCount);
	}
	@Test
	public void name() {

		int threadCount = 20;
		long cacheByteCount = 1024L * 1024L * 100L;

		Cache cache = new Cache(new File("F:/crawler/aaa/"), cacheByteCount);
		OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();

		Crawler crawler = new Crawler(client);
		//crawler.queue.add(HttpUrl.get("https://www.sina.com.cn"));
		crawler.queue.add(HttpUrl.get("http://119.29.113.81"));
		crawler.parallelDrainQueue(threadCount);
	}
}
