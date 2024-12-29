package com.example.authServer.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class JWTHeader {
    private String alg;
    private String typ;
    private String kid;

    public JWTHeader(String alg, String typ, String kid) {
        this.alg = alg;
        this.typ = typ;
        this.kid = kid;

    }

    @JsonProperty("alg")
    public String getAlg() {
        return alg;
    }

    @JsonProperty("typ")
    public String getTyp() {
        return typ;
    }

    @JsonProperty("kid")
    public String getKid() {
        return kid;
    }

}
