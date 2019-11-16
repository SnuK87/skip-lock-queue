package io.github.snuk87;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LockSkipQueueApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LockSkipQueueApplication.class, args);
	}

	@Autowired
	private ImportantJobRepository repo;

	@Autowired
	private JobService service;

	@Override
	public void run(String... args) throws Exception {
		List<ImportantJob> jobs = IntStream.range(0, 20)
				.mapToObj(i -> new ImportantJob(Instant.now()))
				.collect(Collectors.toList());
		repo.saveAll(jobs);

		ExecutorService pool = Executors.newFixedThreadPool(2);
		IntStream.range(0, 20).forEach(i -> pool.submit(() -> service.doJob()));
		pool.shutdown();
	}
}
