package banking.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class BalanceDO {
    private Double amount;
    private Long createdOn;
    private Long updatedOn;

}
