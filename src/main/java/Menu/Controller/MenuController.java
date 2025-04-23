package Menu.Controller;


import Menu.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class MenuController {

    private final MenuService menuService;
}
