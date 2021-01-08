package Interface;

import java.util.List;

import Models.CategoriesPagerModel;

public interface FirestoreInterface {
    void onFirestoreLoadSuccess(List<CategoriesPagerModel> categoriesPagerModelList);
    void onFirestoreFailed(String message);
}
