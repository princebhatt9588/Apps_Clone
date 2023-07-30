import React from 'react'
import styled from 'styled-components'

function Login() {
    return (
        <Container>
            <CTA>
                <CTALogoOne src="/images/cta-logo-one.svg" alt="" />

                <SignUp>
                    GET ALL THERE
                </SignUp>

                <Description>
                    Get Premier Access to Raya and the Last Dragon for an additional fee with 
                    the Disney+ hotstar subscription . As of 01/26/21, the price of Disney+ hotstar and 
                    The Disney Bundle will increase by $1 of the subscription pack.
                </Description>

                <CTALogoTwo src="/images/cta-logo-two.png" alt="" />
            </CTA>
        </Container>
    )
}

export default Login

const Container = styled.div`
    position: relative;
    height: calc(100vh - 70px);
    display: flex;
    align-items: top;
    justify-content: center;


    &:before {
        position: absolute;
        content: "";
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        opacity: 0.7;
        background: url('/images/login-background.jpg') center center / cover no-repeat fixed;
        z-index: -1;
    }
`

const CTA = styled.div`
    max-width: 650px;
    padding: 80px 40px;
    width: 90%;
    display: flex;
    flex-direction: column;
    margin-top: 100px;
    align-items: center;
`

const CTALogoOne = styled.img`

`

const SignUp = styled.a`
    width: 100%;
    background-color: #0063e5;
    font-weight: bold;
    padding: 17px 0;
    color: #f9f9f9;
    border-radius: 4px;
    text-align: center;
    margin-top: 8px;
    margin-bottom: 12px;
    font-size: 18px;
    letter-spacing: 1.5px;
    cursor: pointer;
    transition: all 250ms;

    &:hover {
        background-color: #0483ee;
    }
`

const Description = styled.div`
    font-size: 11px;
    letter-spacing: 1.5px;
    line-height: 1.5;
    text-align: center;
    margin-bottom: 12px;
`

const CTALogoTwo = styled.img`
    width: 90%;
`