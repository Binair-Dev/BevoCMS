package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.repository.PaypalOfferRepository;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.repository.RuleRepository;
import be.bnair.bevo.repository.WikiRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.repository.UserRepository;

@Component
public class DataInit implements InitializingBean {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final WikiRepository wikiRepository;
    private final NewsRepository newsRepository;
    private final PaypalOfferRepository paypalOfferRepository;
    private final RuleRepository ruleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInit(UserRepository userRepository, RankRepository rankRepository, WikiRepository wikiRepository,
    NewsRepository newsRepository, PaypalOfferRepository paypalOfferRepository, RuleRepository ruleRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
        this.wikiRepository = wikiRepository;
        this.newsRepository = newsRepository;
        this.paypalOfferRepository = paypalOfferRepository;
        this.ruleRepository = ruleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //region Création des Rangs
        RankEntity admin = new RankEntity();
        admin.setTitle("Administrateur");
        admin.setPower(100L);
        rankRepository.save(admin);

        RankEntity member = new RankEntity();
        member.setTitle("Membre");
        member.setPower(1L);
        rankRepository.save(member);
        //endregion
        //region Création des Utilisateurs
        UserEntity b_nair = new UserEntity();
        b_nair.setNickname("b_nair");
        b_nair.setEmail("van.bellinghen.brian@gmail.com");
        b_nair.setPassword(passwordEncoder.encode("test1234@"));
        b_nair.setConfirmed(true);
        b_nair.setEnabled(true);
        b_nair.setRank(admin);
        userRepository.save(b_nair);

        UserEntity julie = new UserEntity();
        julie.setNickname("Julie");
        julie.setEmail("frazelle.julie@gmail.com");
        julie.setPassword(passwordEncoder.encode("disneytapeur"));
        julie.setConfirmed(true);
        julie.setEnabled(true);
        julie.setRank(member);
        userRepository.save(julie);
        //endregion
        //region Création des News
        NewsEntity news1 = new NewsEntity();
        news1.setTitle("News 1");
        news1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incid");
        news1.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        news1.setDate(LocalDate.now());
        news1.setPublished(true);
        news1.setAuthor(b_nair);
        newsRepository.save(news1);
        //endregion
        //region Création des Wiki
        WikiEntity wiki1 = new WikiEntity();
        wiki1.setTitle("Titre 1 (Singe)");
        wiki1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.");
        wiki1.setImage("https://geo.img.pmdstatic.net/scale/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Fgeo.2F2022.2F08.2F29.2F78a8d08d-5002-46e1-bc28-5e4a7bdb9ded.2Ejpeg/autox450/quality/65/nasique-2-2.jpg");
        wikiRepository.save(wiki1);

        WikiEntity wiki2 = new WikiEntity();
        wiki2.setTitle("Titre 2");
        wiki2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.");
        wiki2.setImage("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg");
        wikiRepository.save(wiki2);
        //endregion
        //region Création des Offres Paypal
        PaypalOfferEntity paypalOffer1 = new PaypalOfferEntity();
        paypalOffer1.setTitle("5e");
        paypalOffer1.setDescription("5e vous donneront 500 credits");
        paypalOffer1.setPrice(5);
        paypalOffer1.setCredit(500);
        paypalOfferRepository.save(paypalOffer1);

        PaypalOfferEntity paypalOffer2 = new PaypalOfferEntity();
        paypalOffer2.setTitle("10e");
        paypalOffer2.setDescription("10e vous donneront 1000 credits");
        paypalOffer2.setPrice(10);
        paypalOffer2.setCredit(1000);
        paypalOfferRepository.save(paypalOffer2);
        //endregion
        //region Création des Règles
        RuleEntity rule1 = new RuleEntity();
        rule1.setTitle("Ne pas faire d'attaque DDOS envers notre infrastructure");
        rule1.setDescription("Une attaque DDoS, ou par d\u00E9ni de service distribu\u00E9, est un type de cyberattaque qui tente de rendre un site Web ou une ressource r\u00E9seau indisponible en l'inondant de trafic malveillant afin de l'emp\u00EAcher de fonctionner.");
        ruleRepository.save(rule1);
        //endregion
    }
}