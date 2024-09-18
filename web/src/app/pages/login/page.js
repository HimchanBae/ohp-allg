import React, { useState } from 'react';
import {
  Button,
  Checkbox,
  TextInput,
  Form,
  Toggletip,
  ToggletipButton,
  ToggletipContent,
  Grid,
  Column,
} from '@carbon/react';
import { ArrowRight, Information } from '@carbon/icons-react';

export default function LoginPage() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    console.log('Login attempt with', id, password);
  };

  return (
    <Grid className="login-page" fullWidth>
      <Column lg={7} md={4} sm={4} className="login-page__left-column">
        <div className="inner-wrapper">
          <div className="login-page__heading">
            <h1>Log in to IBM</h1>
          </div>

          <div className="form-wrapper">
            <Form onSubmit={handleLogin}>
              <TextInput
                id="userid"
                invalidText="Email or IBMid is required"
                labelText="IBMid"
                placeholder="username@ibm.com"
                value={id}
                onChange={(e) => setId(e.target.value)}
              />
              <Button
                className="continueBtn"
                type="submit"
                kind="primary"
                size="large-productive"
                renderIcon={ArrowRight}
              >
                Continue
              </Button>
            </Form>

            <div className="checkbox-row">
              <Checkbox labelText="Remember Me" id="remember-Me" />
              <Toggletip>
                <ToggletipButton label="Show information">
                  <Information />
                </ToggletipButton>
                <ToggletipContent>
                  <p>
                    You can opt to have your IBMid remembered the next time you
                    access our website by checking the "Remember Me" box. If you
                    do not wish to have your IBMid remembered the next time you
                    access our website, leave the "Remember Me" box unchecked.
                  </p>
                </ToggletipContent>
              </Toggletip>
            </div>
          </div>

          <div className="login-page__text">
            <span className="account-text">Don't have an account? </span>
            <Button
              type="submit"
              kind="tertiary"
              size="large-productive"
              renderIcon={ArrowRight}
              onClick={() =>
                (window.location.href = 'https://www.ibm.com/account/reg')
              }
            >
              Create an IBMid
            </Button>
            <div className="forgot-section">
              <span>Forgot IBMid? </span>
              <a href="https://www.ibm.com/docs/en/ibmid?topic=ibmid-forgot-your">
                Contact the IBMid help desk
              </a>
            </div>
          </div>
        </div>
      </Column>

      <Column lg={9} md={4} sm={4} className="right-column">
        <img
          src="/images/login_image.gif"
          alt="CODB 2024 ambient animation loop"
        ></img>
      </Column>
    </Grid>
  );
}
