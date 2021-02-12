import React, {useState} from 'react';
import Button from 'react-bootstrap/Button'
import LoginForm from './../../Components/LoginForm/LoginForm';
import './Login.css';

function LoginMain() {
    const adminUser = {
        name: "admin",
        password: "admin123"
    }

    const [user, setUser] = useState({name: ""});
    const [error, setError] = useState("");

    const Login = details => {
        console.log(details);

        if(details.name===adminUser.name && details.password===adminUser.password){
            console.log("Logged in");
            setUser({name: details.name});
        }
        else{
            console.log("Details do not match!");
            setError("Details do not match!")
        }
       
    }

    const Logout = () => {
        setUser({name: ""});
    }

    return (
        <div className='login-page'>
            {(user.name !== "") ? (
                <div className="welcom">
                    <h2>Welcome, <span>{user.name}</span></h2>
                    <Button variant="success" type="submit" onClick={Logout}>
                        Logout
                    </Button>
                </div> 
            ) : (
                <LoginForm Login={Login} error={error}/>
            )}
        </div>
    )
}

export default LoginMain

