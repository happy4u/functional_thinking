import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public String cleanNames(List<String> names) {
        if (names == null) return "";
        return names
               .stream()
               .filter(name -> name.length() > 1)
               .map(name -> capitalize(name))
               .collect(Collectors.joining(","));
}
private String capitalize(String e) {
        return e.substring(0, 1).toUpperCase() + e.substring(1, e.length());
}
