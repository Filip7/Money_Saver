package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import lombok.NoArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
public class MoneyAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void testUserResetSession() throws Exception {
        this.mockMvc
                .perform(get("/reset-wallet")
                        .with(user("filip")
                                .password("filippass")
                                .roles("USER"))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testShowSearchForm() throws Exception {
        this.mockMvc
                .perform(get("/expenses/searchForm")
                        .with(user("admin")
                                .password("adminpass")
                                .roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("expense"))
                .andExpect(model().attributeExists("flag"))
                .andExpect(model().attribute("flag", Matchers.equalTo("true")))
                .andExpect(view().name("searchExpenses"));
    }

    @Test
    public void testFindTrosak() throws Exception {
        Expense trosak = new Expense();
        trosak.setName("Test1");
        trosak.setTypeOfExpense(TypeOfExpense.HRANA);
        trosak.setCost(new BigDecimal(100));

        this.mockMvc
                .perform(post("/expenses/search")
                        .flashAttr("expense", trosak)
                        .flashAttr("flag", "true")
                        .with(user("admin")
                                .password("adminpass")
                                .roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("expenses"))
                .andExpect(model().attribute("expenses", Matchers.hasSize(1)))
                .andExpect(view().name("searchExpenses"));
    }

    @Test
    public void testNotFoundTrosak() throws Exception {
        Expense trosak = new Expense();
        trosak.setName("Pardon, mais non");

        this.mockMvc
                .perform(post("/expenses/search")
                        .flashAttr("expense", trosak)
                        .flashAttr("flag", "true")
                        .with(user("admin")
                                .password("adminpass")
                                .roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("expenses"))
                .andExpect(view().name("searchExpenses"));
    }

    @Test
    public void testAddTrosak() throws Exception {

        Expense trosak = new Expense();
        trosak.setName("Gyros iz paprike");
        trosak.setTypeOfExpense(TypeOfExpense.HRANA);
        trosak.setCost(BigDecimal.TEN); // plus minus...
        trosak.setWallet(walletRepository.findById(1L).get());

        this.mockMvc
                .perform(post("/expenses")
                        .flashAttr("expense", trosak)
                        .with(user("admin")
                                .password("adminpass")
                                .roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("expense"))
                .andExpect(model().attributeHasNoErrors("expense"))
                .andExpect(view().name("expenseAccepted"));
    }
}