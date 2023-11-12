package christmas.badge;

import christmas.badge.model.Badge;
import christmas.benefit.model.Benefit;
import christmas.benefit.model.Benefits;

public class BadgeService {
    public Badge getBadge(Benefits benefits){
        return Badge.from(benefits.getTotalPrice());
    }
}
