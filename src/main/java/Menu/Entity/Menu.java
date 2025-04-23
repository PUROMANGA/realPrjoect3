package Menu.Entity;

import com.example.minzok.global.base_entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Store;

import java.util.ArrayList;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menus")

public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menu_name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    /**
     * 리뷰 연관관계
     */

    @OneToMany(mappedBy = "toMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews = New ArrayList<>();

    public Menu(Name name ,Menu_name menu_name, Rating rating, Contents contents) {
            this.name = name;
            this.menu_name = menu_name;
            this.rating = rating;
            this.contents = contents;
    }

    public void update(menuRequestDto dto) {
        this.contents = dto.getContents();
    }
}
