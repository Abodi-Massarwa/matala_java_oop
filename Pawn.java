public class Pawn extends ConcretePiece {
    public Pawn(Player owner) {
        super();
        setOwner(owner);
    }

    @Override
    public String getType() {
        return "♟";
    }
}