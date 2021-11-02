package com.github.akafasty.aprire.enums;

import lombok.Getter;

@Getter
public enum Role {

    RECRUIT("Recruta", "-"),
    MEMBER("Membro", "+"),
    OFFICER("Oficial", "*"),
    OWNER("Dono", "#");

    private final String name, symbol;

    Role(String name, String symbol) { this.name = name; this.symbol = symbol; }

    public static Role fromString(String string) {

        try { return valueOf(string); }
        catch (Exception exception) { return null; }

    }

    public static Role fromInt(int value) {

        switch (value) {

            case 0: return RECRUIT;

            case 1: return MEMBER;

            case 2: return OFFICER;

            case 3: return OWNER;

        }

        return null;

    }

    public int getValue() { return this.ordinal(); }

}
