package com.hotelbeds.supplierintegrations.hackertest.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Setter;

@RedisHash(value="risk", timeToLive = 300)
@Setter
public class FailedLogin {
	@Id
	private String id;
	@Indexed
	private String ip;
	private long timestamp;
}
