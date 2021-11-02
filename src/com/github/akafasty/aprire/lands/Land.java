package com.github.akafasty.aprire.lands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Land {

    private int x, z;
    private String faction, world;

}
