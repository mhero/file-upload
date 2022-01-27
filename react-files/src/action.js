import axios from "axios";
const BASE_URL = "http://localhost:8080/document";
const API_UPLOAD = "/upload/";
const API_DOWNLOAD = "/download/";

export const uploadNewFile = (states) => {
  const formData = new FormData();
  formData.append("file", states.state.selectedFile);

  axios
    .post(`${BASE_URL}${API_UPLOAD}`, formData)
    .then((res) => {
      states.setState({
        message:
          "You can download your file here " +
          `${BASE_URL}/${res.data.id}${API_DOWNLOAD}`,
        meesageClass: "ok",
      });
    })
    .catch(function (error) {
      let errorMessage = "";
      console.log(error);
      if (error.response == null || error.response.status === 500) {
        errorMessage = "Server error...";
      }
      errorMessage = "Something went wrong";

      states.setState({
        message: errorMessage,
        meesageClass: "error",
      });
    });
};
