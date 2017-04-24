package org.alvin.opsdev.monitor.system.domain;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "acc_act")
public class AccountAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "act_id",referencedColumnName = "id")
    private Action action;
    @ManyToOne
    @JoinColumn(name = "acc_id",referencedColumnName = "id")
    private Account account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
