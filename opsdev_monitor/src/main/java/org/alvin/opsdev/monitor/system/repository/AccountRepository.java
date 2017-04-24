package org.alvin.opsdev.monitor.system.repository;

import org.alvin.opsdev.monitor.system.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
