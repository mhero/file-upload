import React from "react";
import { render } from "react-dom";
import { MyUploader } from "./react-uploader";
import "./index.css";

const App = () => (
  <div>
    <MyUploader />
  </div>
);

render(<App />, document.getElementById("root"));
