import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import {useParams} from 'react-router-dom';
import db from '../firebase';
import { collection, getDocs } from "firebase/firestore"; 

const Detail=()=> {
    const {id} = useParams();
    const [movie, setMovie] = useState({});


    const fetchMovie = async (ID)=>{
        try{
                const querySnapshot = await getDocs(collection(db, "movies"));
                querySnapshot.forEach(doc => {
                    if(doc.id === ID){
                        return setMovie((doc.data()));
                    }
                });
        }catch(error){
            alert(error.message)
        }
    };

    useEffect(() => {
        fetchMovie(id);

        return () => {
            setMovie({});
          };

    }, [id])

    console.log(movie)

    return (
        <Container>
            {movie && (
                <>
                    <Background>
                        <img src={movie.backgroundImg} alt='Background' />
                    </Background>

                    <ImageTitle>
                        <img src={movie.titleImg} alt='Title' />
                    </ImageTitle>

                    <Controls>
                        <PlayButton>
                            <img src='/images/play-icon-black.png' alt='' />
                            <span>PLAY</span>
                        </PlayButton>
                        <TrailerButton>
                            <img src='/images/play-icon-white.png' alt='' />
                            <span>TRAILER</span>
                        </TrailerButton>
                        <AddButton>
                            <span>+</span>
                        </AddButton>
                        <GroupWatchButton>
                            <img src='/images/group-icon.png' alt='' />
                        </GroupWatchButton>
                    </Controls>

                    <SubTitle>
                        {movie.subTitle}
                    </SubTitle>
                    
                    <Description>
                        {movie.description}
                    </Description>
                </>
            )}
        </Container>
    )
}

export default Detail

const Container = styled.div`
    min-height: calc(100vh - 70px);
    padding: 0 calc(3.5vw + 5px);
    position: relative;
`

const Background = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: -1;
    opacity: 0.7;

    img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
`

const ImageTitle = styled.div`
    height: 30vh;
    min-height: 170px;
    width: 35vw;
    min-width: 200px;
    margin-top: 50px;
    margin-bottom: 10px;
    left: 0;

    img {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
`

const Controls = styled.div`
    display: flex;
    align-items: center;
`

const PlayButton = styled.button`
    height: 56px;
    border: 3px solid rgba(249, 249, 249);
    padding: 0px 24px;
    margin-right: 22px;
    border-radius: 5px;
    font-size: 15px;
    letter-spacing: 1.8px;
    display: flex;
    align-items: center;
    cursor: pointer;

    &:hover {
        background: rgb(198, 198, 198, 0.7);
    }
`

const TrailerButton = styled(PlayButton)`

    color: white;
    background: rgba(0, 0, 0, 0.3);
    border: 1px solid rgba(249, 249, 249);

    &:hover {
        background: rgb(198, 198, 198, 0.5);
    }
`

const AddButton = styled.button`
    height: 44px;
    width: 44px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 16px;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.7);
    border: 1px solid rgba(249, 249, 249);
    cursor: pointer;

    span {
        font-size: 30px;
        color: white;
    }

    &:hover {
        background: rgb(198, 198, 198, 0.2);
    }
`

const GroupWatchButton = styled(AddButton)`
    background: rgba(0, 0, 0, 0.9);
`

const SubTitle = styled.div`
    font-size: 15px;
    color: rgba(249, 249, 249);
    min-height: 20px;
    margin-top: 26px;
`

const Description = styled.div`
    
    font-size: 20px;
    margin-top: 16px;
    max-width: 700px;
    color: rgba(249, 249, 249);
`