package hr.java.web.milkovic.moneyapp.controller.rest;

import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/wallet", produces = "application/json")
@CrossOrigin
public class WalletRestController {
    private final WalletRepository repository;

    public WalletRestController(WalletRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Wallet> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findOne(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(wallet -> new ResponseEntity<>(wallet, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public Wallet save(@RequestBody Wallet wallet) {
        return repository.save(wallet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteWalletById(id);
    }
}
