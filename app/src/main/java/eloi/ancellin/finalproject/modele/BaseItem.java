package eloi.ancellin.finalproject.modele;

public class BaseItem {

    protected String description;
    protected boolean descExist;


    BaseItem(){
        description = "";
        descExist = false;
    }

    BaseItem(String _description){
        description = _description;
        descExist = true;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseItem other = (BaseItem) obj;
        if (descExist != other.descExist)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isDescExist() {
        return descExist;
    }
    public void setDescExist(boolean descExist) {
        this.descExist = descExist;
    }
}
