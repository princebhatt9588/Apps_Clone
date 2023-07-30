import React, { useEffect } from 'react';
import styled from 'styled-components';
import ImgSlider from './ImgSlider';
import Viewers from './Viewers';
import Movies from './Movies';
import db from '../firebase';
import { onSnapshot, collection, query } from "firebase/firestore";
import { useDispatch } from 'react-redux';
import { setMovies } from '../features/movie/movieSlice';

function Home() {
    const dispatch = useDispatch();


    useEffect(() => {
        const q = query(collection(db, "movies"))
        onSnapshot(q, (querySnapshot) => {
            let tempMovies = querySnapshot.docs.map((doc) => {
                return { id: doc.id, ...doc.data() }
            })
            dispatch(setMovies(tempMovies))
        });
    })


    //useEffect is to run the effect after rendering first the page
    //db has movies collection as snapshot
    //docs has all the details like img, title, desc etc..
    //map is used to iterate the docs
    //doc is the current iter val that is passed
    //doc.data is unpacked by using ...
    //after unpacking, the doc.data and the doc.id are returned

    return (
        <Container>
            <ImgSlider />
            <Viewers />
            <Movies />
        </Container>
    )
}

export default Home

const Container = styled.main`
    min-height: calc(100vh - 70px);
    padding: 0 calc(3.5vw + 5px);
    position: relative;
    overflow-x: hidden;
    
    &:before {
        background: url("/images/home-background.png") center center / cover no-repeat fixed;
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: -1;
    }

    
`