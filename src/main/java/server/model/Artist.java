package server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Artist {
    private int id;
    private String name;
    private String surname;
}
