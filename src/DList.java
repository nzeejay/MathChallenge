public class DList{
    Item head;

    public DList() {
        head = new Item();
    }

    public String toString() {
        String str = "";

        if(head.next == head)
            return "List Empty";

        str = "list content = ";

        for(Item current = head.next; current != head && current != null; current = current.next)
            str += String.format("%.2f(%.2f%s%.2f), ",  current.data.Answer,
                                                        current.data.Number1,
                                                        current.data.getOperator(),
                                                        current.data.Number2);

        return str;
    }
}

class Item {
    Item prev;
    Item next;
    Question data;

    public Item() {
        prev = this;
        next = this;
        data = null;
    }

    public Item(Question data) {
        prev = null;
        next = null;
        this.data = data;
    }

    //add after this item
    public void Append(Item newItem) {
        newItem.prev = this;
        newItem.next = next;

        if(next != null)
            next.prev = newItem;

        next = newItem;
    }

    //add before this item
    public void Insert(Item newItem) {
        newItem.prev = prev;
        newItem.next = this;

        prev.next = newItem;

        prev = newItem;
    }

    public void Remove() {
        next.prev = prev;
        prev.next = next;
    }
}
