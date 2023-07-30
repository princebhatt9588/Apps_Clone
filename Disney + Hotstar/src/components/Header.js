import React , { useEffect } from 'react'
import { auth, provider } from "../firebase"
import { signInWithPopup } from '@firebase/auth'
import { useNavigate } from "react-router-dom"
import styled from 'styled-components'
import {
    selectUserName,
    selectUserPhoto,
    setUserLogin,
    setSignOut
} from '../features/user/userSlice'
import { useSelector, useDispatch } from 'react-redux'

function Header() {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const userName = useSelector(selectUserName);
    const userPhoto = useSelector(selectUserPhoto);

    useEffect(() => {
        auth.onAuthStateChanged(async (user) => {
            if(user){
                dispatch(setUserLogin({
                    name: user.displayName,
                    email: user.email,
                    photo: user.photoURL,
                }))
                navigate('/');
            }
        })
    } , [userName])



    const signIn = () => {
        signInWithPopup(auth, provider)
        .then((result) => {
            let user = result.user
            dispatch(setUserLogin({
                name: user.displayName,
                email: user.email,
                photo: user.photoURL,
            }))
            navigate('/');
        })
    }

    const signOut = () => {
        auth.signOut()
        .then(()=>{
            dispatch(setSignOut());
            navigate('/login');
        })
    }

    return (
        <Nav>
            <Logo src="/images/logo.svg" />
            { !userName ? ( 
                    <Login onClick={signIn}>LOGIN</Login> ):
                <>
                    <NavMenu>
                        <a href='/'>
                            <img src='/images/home-icon.svg' alt='home' />
                            <span>HOME</span>
                        </a>
                        <a href='/'>
                            <img src='/images/search-icon.svg' alt='search' />
                            <span>SEARCH</span>
                        </a>
                        <a href='/'>
                            <img src='/images/watchlist-icon.svg' alt='watchlist' />
                            <span>WATCHLIST</span>
                        </a>
                        <a href='/'>
                            <img src='/images/original-icon.svg' alt='originals' />
                            <span>ORIGINALS</span>
                        </a>
                        <a href='/'>
                            <img src='/images/movie-icon.svg' alt='movies' />
                            <span>MOVIES</span>
                        </a>
                        <a href='/'>
                            <img src='/images/series-icon.svg' alt='series' />
                            <span>SERIES</span>
                        </a>
                    </NavMenu>

                    <UserImg src={userPhoto} onClick={signOut} />
                </>
            }

            

        </Nav>
    )
}

export default Header

const Nav = styled.nav`
    height: 70px;
    background: #090b13;
    position:fixed; top:0; left:0; right:0;
    display:flex; justify-content:space-between; align-items:center;
    padding: 0 36px;
    overflow-x: hidden;
    z-index:3;
`

const Logo = styled.img`
    width: 80px;
    cursor: pointer;
`

const NavMenu = styled.div`
    display: flex;
    flex: 1;
    align-items: center;
    margin-left: 25px;

    a {
        display: flex;
        align-items: center;
        padding: 0 12px;
        cursor: pointer;
        text-decoration: none;
        color: white;

        img {
            height: 20px;
        }

        span {
            font-size: 15px;
            letter-spacing: 1.42px;
            position: relative;

            &:after {
                content: "";
                height: 2px;
                background: white;
                position: absolute;
                left: 0;
                right: 0;
                bottom: -4px;
                transform-origin: left center;
                transition: all 250ms cubic-bezier(0.25, 0.46, 0.45, 0.94) 0s;
                transform: scaleX(0);
            }
        }

        &:hover {
            span:after {
                transform: scaleX(1);
                opacity: 1;
            }
        }
    }
`

const UserImg = styled.img`
    width: 48px;
    height: 48px;
    border-radius: 50%;
    cursor: pointer;
`

const Login = styled.div`
    border: 1px solid #f9f9f9;
    border-radius: 5px;
    padding: 6px 16px;
    letter-spacing: 1.5px;
    background-color: rgba(0, 0, 0, 0.6);
    transition: all 0.2s ease 0s;
    cursor: pointer;

    &:hover {
        background-color: white;
        color: black;
        border-color: transparent;
    }
`
