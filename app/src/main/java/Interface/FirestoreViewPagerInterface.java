package Interface;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirestoreViewPagerInterface {
    void onItemClick(DocumentSnapshot snapshot, int position);
}
