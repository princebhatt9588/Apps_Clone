import { initializeApp } from "firebase/app"
import { getFirestore } from 'firebase/firestore';
import { getAuth } from 'firebase/auth';
import { GoogleAuthProvider } from "firebase/auth";
import { getStorage } from 'firebase/storage';

const firebaseConfig = {
    apiKey: "AIzaSyAYYTMJ5f542MM3RxNLz0hsXVY0fihyQl4",
    authDomain: "disney-plus-unofficial.firebaseapp.com",
    projectId: "disney-plus-unofficial",
    storageBucket: "disney-plus-unofficial.appspot.com",
    messagingSenderId: "306515018549",
    appId: "1:306515018549:web:d15552e0ca086ed2af427c"
};

const firebaseApp = initializeApp(firebaseConfig);
const db = getFirestore();
const auth = getAuth();
const provider = new GoogleAuthProvider();
const storage = getStorage();

export { firebaseApp, auth, provider, storage };
export default db;