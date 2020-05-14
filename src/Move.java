enum MovePerson{
    Goose0,
    Goose1,
    Goose2,
    Goose3,
    Fox
}

public class Move {
    MovePerson person;
    MoveDir dir;

    Move(MovePerson person, MoveDir dir){
        this.person = person;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "Move{" +
                "person=" + person.name() +
                ", dir=" + dir.name() +
                '}';
    }
}
