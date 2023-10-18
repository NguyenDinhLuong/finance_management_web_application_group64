import React, { Component } from 'react';
import axios from 'axios';

class FileUpload extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedFile: null,
            loaded: 0
        }
    }


    onChangeHandler=event=>{
        var files = event.target.files[0]
        // if return true allow to setState
        this.setState({
            selectedFile: files,
            loaded:0
        })
        console.log(files)
    }

    onClickHandler = () => {
        const data = new FormData()
        data.append('pic', this.state.selectedFile)

        let professor_id = 0;

        const url = "/api/"

        axios.post(url, data)
            .then(res => { // then print response status
                console.log(this.state.selectedFile)
                console.log(res.data);
                professor_id = res.data;
            }).catch(err => { // then print response status
            console.log('upload fail')
        })

    }

    render() {
        return (

            <div class="container">
                <div class="row">
                    <div class="offset-md-3 col-md-6">
                        <div class="form-group files">
                            <input type="file" name="file" onChange={this.onChangeHandler}/>
                        </div>
                        <div class="form-group">
                        </div>

                        <button type="button" class="btn btn-success btn-block" onClick={this.onClickHandler}>Upload</button>

                    </div>
                </div>
            </div>
        );
    }

}

export default FileUpload;
