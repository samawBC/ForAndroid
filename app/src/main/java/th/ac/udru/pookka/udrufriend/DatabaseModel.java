package th.ac.udru.pookka.udrufriend;

import android.os.Parcel;
import android.os.Parcelable;

public class DatabaseModel implements Parcelable {
    private  String uidString,pathUrlString, nameString;
    private  Double latDouble , lngDouble;


    public DatabaseModel() {

    } // constructor  Getter

    public DatabaseModel(String uidString, String pathUrlString, String nameString, Double latDouble, Double lngDouble) {
        this.uidString = uidString;
        this.pathUrlString = pathUrlString;
        this.nameString = nameString;
        this.latDouble = latDouble;
        this.lngDouble = lngDouble;
    }

    protected DatabaseModel(Parcel in) {
        uidString = in.readString();
        pathUrlString = in.readString();
        nameString = in.readString();
        if (in.readByte() == 0) {
            latDouble = null;
        } else {
            latDouble = in.readDouble();
        }
        if (in.readByte() == 0) {
            lngDouble = null;
        } else {
            lngDouble = in.readDouble();
        }
    }

    public static final Creator<DatabaseModel> CREATOR = new Creator<DatabaseModel>() {
        @Override
        public DatabaseModel createFromParcel(Parcel in) {
            return new DatabaseModel(in);
        }

        @Override
        public DatabaseModel[] newArray(int size) {
            return new DatabaseModel[size];
        }
    };

    public String getUidString() {
        return uidString;
    }

    public void setUidString(String uidString) {
        this.uidString = uidString;
    }

    public String getPathUrlString() {
        return pathUrlString;
    }

    public void setPathUrlString(String pathUrlString) {
        this.pathUrlString = pathUrlString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public Double getLatDouble() {
        return latDouble;
    }

    public void setLatDouble(Double latDouble) {
        this.latDouble = latDouble;
    }

    public Double getLngDouble() {
        return lngDouble;
    }

    public void setLngDouble(Double lngDouble) {
        this.lngDouble = lngDouble;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uidString);
        dest.writeString(pathUrlString);
        dest.writeString(nameString);
        if (latDouble == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latDouble);
        }
        if (lngDouble == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lngDouble);
        }
    }
}    //main class
