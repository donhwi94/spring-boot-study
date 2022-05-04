package io.nadonhwi.boot.storejpastore.jpo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="TRAVEL_CLUB")
public class TravelClubJpo {

	@Id
	private String id;
	private String name;
	private String intro;
	private long foundationTime;
}
