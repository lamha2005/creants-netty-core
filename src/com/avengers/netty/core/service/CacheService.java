package com.avengers.netty.core.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avengers.netty.core.util.AppConfig;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author LamHa
 *
 */
public class CacheService {
	private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);
	private Cluster cluster;
	private Bucket bucket;

	private static CacheService instance;

	public static CacheService getInstance() {
		if (instance == null) {
			instance = new CacheService();
		}

		return instance;
	}

	private CacheService() {
		try {
			LOG.info("---------------------- INIT COUCHBASE --------------------");
			CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
					.connectTimeout((int) TimeUnit.SECONDS.toMillis(45)).kvTimeout(TimeUnit.SECONDS.toMillis(60))
					.computationPoolSize(3).ioPoolSize(3).build();

			cluster = CouchbaseCluster.create(env, AppConfig.cacheHosts);
			bucket = cluster.openBucket(AppConfig.bucketName, AppConfig.cachePassword);
			if (bucket == null) {
				LOG.error("[ERROR] Cache service can't get bucket");
			}

			LOG.info("::::::::::::::::::::::::::::::::::::::::::::::::::::");
			LOG.info(">> COUCHBASE STARTED");
			LOG.info(String.format(">> [cacheHosts=%s, bucketName=%s]", AppConfig.cacheHosts.toString(),
					AppConfig.bucketName));
			LOG.info("::::::::::::::::::::::::::::::::::::::::::::::::::::");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	public void upsert(String key, String jsonString) {
		upsert(key, 0, jsonString);
	}

	public void upsert(String key, int expireSecond, String jsonString) {
		bucket.upsert(RawJsonDocument.create(key, expireSecond, jsonString));
	}

	public String get(String key) {
		RawJsonDocument json = bucket.get(key, RawJsonDocument.class);
		if (json != null) {
			return json.content();
		}

		return null;
	}

	public void delete(String key) {
		bucket.remove(key);
	}

	public List<RawJsonDocument> getBulk(final Collection<String> keys) {
		return Observable.from(keys).flatMap(new Func1<String, Observable<RawJsonDocument>>() {
			@Override
			public Observable<RawJsonDocument> call(String id) {
				return bucket.async().get(id, RawJsonDocument.class);
			}
		}).toList().toBlocking().single();
	}

	public void shutdown() {
		LOG.info("Destroy extension - Shutdown Couchbase");
		if (cluster != null) {
			bucket.close();
			cluster.disconnect();
		}
	}
}
