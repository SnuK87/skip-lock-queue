package io.github.snuk87;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);
	private final ImportantJobRepository repo;

	@Transactional
	public void doJob() {
		log.info("doJob...");
		repo.findTopByProcessedAtIsNull().ifPresent(job -> {
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(6) * 1000L);
				job.setProcessedAt(Instant.now());
				job.setProcessedBy(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				Thread.currentThread().interrupt();
			}
		});
	}
}
