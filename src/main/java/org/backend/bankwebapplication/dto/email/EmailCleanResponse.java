package org.backend.bankwebapplication.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCleanResponse {
    private String source;
    private String email;
    private String local;
    private String domain;
    private String type;
    private int qc;

    public boolean isPersonal() {
        return this.type.equals("PERSONAL");
    }
}
