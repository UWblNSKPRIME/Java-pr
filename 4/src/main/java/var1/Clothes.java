package var1;

import java.util.Objects;

/** Предмет одягу з назвою, розміром та ціною. */
public class Clothes {
    private String name;
    private String size;
    private double price;

    public Clothes(String name, String size, double price) {
        setName(name);
        setSize(size);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Size cannot be empty");
        }
        this.size = size.trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Price must be a nonnegative number");
        }
        this.price = price == 0 ? 0 : price;
    }

    @Override
    public String toString() {
        return "Clothes{name='" + name + "', size='" + size + "', price=" + price + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Clothes other = (Clothes) object;
        return name.equals(other.name) && size.equals(other.size)
                && Double.compare(price, other.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, price);
    }
}
