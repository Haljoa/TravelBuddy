package org.travel.Core.Domain;

public class Line {
    private String id;
    private String publicCode;

    public Line() {
    }

    public Line(String id, String publicCode) {
        this.id = id;
        this.publicCode = publicCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    @Override
    public String toString() {
        return "Line {" +
                "id = " + id +
                "publicCode = " + publicCode +
                "}";
    }
}