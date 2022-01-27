import * as React from "react";
import * as Action from "./action";

export class MyUploader extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      selectedFile: null,
      message: null,
      meesageClass: null,
    };
  }

  fileUploadHanlder = () => {
    let self = this;
    Action.uploadNewFile(self);
  };

  fileSelectedHandler = (event) => {
    this.setState({
      selectedFile: event.target.files[0],
    });
  };

  render() {
    return (
      <div className="container">
        <input
          className="title"
          type="file"
          onChange={this.fileSelectedHandler}
        />
        <button className="button" onClick={this.fileUploadHanlder}>
          Upload
        </button>
        <br />
        <label className={this.state.meesageClass}>{this.state.message}</label>
      </div>
    );
  }
}
