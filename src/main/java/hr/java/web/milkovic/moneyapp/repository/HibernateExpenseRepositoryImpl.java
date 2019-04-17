package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.Wallet;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Primary
@Repository
@Transactional
public class HibernateExpenseRepositoryImpl implements ExpenseRepository {

    private final EntityManager entityManager;
    private final WalletRepository walletRepository;

    public HibernateExpenseRepositoryImpl(EntityManager entityManager,
                                          WalletRepository walletRepository) {
        this.entityManager = entityManager;
        this.walletRepository = walletRepository;
    }

    @Override
    public Iterable<Expense> findAll() {
        return entityManager
                .createQuery("select e  from Expense e", Expense.class)
                .getResultList();
    }

    @Override
    public Iterable<Expense> findAllByWalletId(Long id) {
        return entityManager
                .createQuery("select e from Expense e where e.walletId = ?1", Expense.class)
                .setParameter(1, id)
                .getResultList();
    }

    @Override
    public Expense findById(Long id) {
        return entityManager
                .createQuery("select e from Expense e where e.id = ?1", Expense.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    @Override
    public Expense save(Expense expense) {
        return entityManager.merge(expense);
    }

    @Override
    public Integer deleteByWalletId(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId);
        Expense expense = entityManager
                .createQuery("select e from Expense e where e.wallet = ?1", Expense.class)
                .setParameter(1, wallet)
                .getSingleResult();

        entityManager.remove(expense);

        return Integer.valueOf(walletId.toString());
    }
}
