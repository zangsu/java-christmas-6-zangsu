package christmas.domain.badge;

import christmas.domain.badge.model.Badge;
import christmas.domain.benefit.model.Benefits;

public class BadgeService {
    public Badge getBadge(Benefits benefits) {
        return Badge.from(benefits.getTotalPrice());
    }
}
