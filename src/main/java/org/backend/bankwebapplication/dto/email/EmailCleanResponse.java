package org.backend.bankwebapplication.dto.email;

public record EmailCleanResponse(String source, String email, String local, String domain, String type, int qc) {
    public boolean isPersonal() {
        return this.type.equals("PERSONAL");
    }
}
