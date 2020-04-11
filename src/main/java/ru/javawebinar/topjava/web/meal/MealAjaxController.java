package ru.javawebinar.topjava.web.meal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ajax/meals")
public class MealAjaxController extends AbstractMealController {
}
