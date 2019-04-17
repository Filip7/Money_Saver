package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@Transactional
public class HibernateWalletRepositoryImpl implements WalletRepository {

    private final EntityManager entityManager;

    public HibernateWalletRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Iterable<Wallet> findAll() {
        try {
            return entityManager
                    .createQuery("select w from Wallet w", Wallet.class)
                    .getResultList();
        } catch (Exception e) {
            log.error("No wallet in database");
            return null;
        }
    }

    @Override
    public Wallet findById(Long id) {
        return entityManager
                .createQuery("select w from Wallet w where w.id = ?1", Wallet.class)
                .setParameter(1, id).getSingleResult();
    }

    @Override
    public Wallet save(Wallet wallet) {
        return entityManager.merge(wallet);
    }

    @Override
    public Optional<Wallet> findByUserId(Long userId) {
        Wallet wallet = null;
        try {
            wallet = entityManager
                    .createQuery("select w from Wallet w where w.userId = ?1", Wallet.class)
                    .setParameter(1, userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.error("No wallet for user id {}", userId);
        }

        return Optional.ofNullable(wallet);
    }
}
