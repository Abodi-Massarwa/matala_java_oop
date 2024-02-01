public class King extends ConcretePiece {

    public King(Player owner) {
        super();
        setOwner(owner);
    }
    @Override
    public String getType() {
        return "♔";
    }
}
