export interface BasicUserProps {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
}

export interface DecodedToken extends BasicUserProps {
  id: string;
  exp: number;
  iat: number;
}
