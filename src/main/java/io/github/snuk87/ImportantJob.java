package io.github.snuk87;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ImportantJob {

	@Id
	@GeneratedValue
	private Long id;
	private Instant createdAt;
	private Instant processedAt;
	private String processedBy;

	public ImportantJob(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
