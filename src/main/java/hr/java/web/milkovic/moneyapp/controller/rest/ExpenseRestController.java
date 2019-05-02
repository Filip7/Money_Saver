package hr.java.web.milkovic.moneyapp.controller.rest;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.repository.ExpenseRepository;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/expense", produces = "application/json")
@CrossOrigin
public class ExpenseRestController {
    private final ExpenseRepository repository;
    private final WalletRepository walletRepository;

    public ExpenseRestController(ExpenseRepository repository,
                                 WalletRepository walletRepository) {
        this.repository = repository;
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Expense> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findOne(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(expense -> new ResponseEntity<>(expense, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public Expense save(@RequestBody Expense expense) {
        return repository.save(expense);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteExpenseById(id);
    }

}
