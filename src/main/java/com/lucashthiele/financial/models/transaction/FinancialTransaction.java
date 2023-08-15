package com.lucashthiele.financial.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialTransaction {
    @Id
    private String id;
    private Double value;
    private String type;
    private String origin;
}
