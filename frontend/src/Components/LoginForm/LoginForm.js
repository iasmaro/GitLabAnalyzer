import React, {useState} from 'react';
import {Form} from "react-bootstrap";
import Button from 'react-bootstrap/Button'
import './LoginForm.css';


function LoginForm({Login, error}) {
    const [details, setDetails] = useState({name: "", password: "" });

    const submitHandler = e => {
        e.preventDefault();

        Login(details);
    }
        

    return (
        <Form className="login-form" onSubmit={submitHandler}>
            <div  className="form-inner">
               <h2>Login</h2>
               {(error !== "") ? (<div className="error">{error}</div> ) : ""}

               <Form.Group controlId="name">
                    <Form.Label>SFU ID Userame:</Form.Label>
                    <Form.Control type="name" placeholder="Enter username" name="name"  required="required" onChange={e => setDetails({...details, name:e.target.value})} value={details.name}/>
                </Form.Group>
      
                <Form.Group controlId="password">
                    <Form.Label>Password:</Form.Label>
                    <Form.Control type="password" placeholder="Password" name="password" required="required" onChange={e => setDetails({...details, password:e.target.value})} value={details.password}/>
                </Form.Group>

                <Button variant="success" type="submit">
                    Sign in
                </Button>

            </div>
      </Form>
    );
}

export default LoginForm;

