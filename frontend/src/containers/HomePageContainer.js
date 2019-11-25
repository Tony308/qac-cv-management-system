import React, {Component} from 'react';
import HomePageComponent from "../components/HomePageComponent";
import axios from 'axios';
export default class HomePageContainer extends Component {
    constructor(props){
        super(props);

        this.state = {
            data: []
        }
    }

    retrieveCV(id) {
        let url = 'http://localhost:8081/cvsystem/retrieve/' + id;

        axios.get(url)
            .then(res => {
                this.setState({
                    data: res.data
                });
                // console.log(res.data.cvFile.data);
                document.getElementById("output").value = res.data.cvFile.data;
                console.log(res.data.cvFile);

            })
            .catch(err => console.log(err))
    }

    listCVs(rows, data) {
        for (let x  = 0; x < data.length; x++) {
            let row = data[x];

            rows.push(
                <tr key={x}>
                    <td onClick={() => {
                        this.retrieveCV(row.id)
                    }}>{row.name}</td><td>{row.fileName}
                    </td>
                    <td>
                        <button className="btn" onClick={() => {
                            this.props.deleteCV(row.id)
                        }}><i className="fa fa-trash"/></button>
                    </td>
                    <td> <button onClick={() => {
                        this.props.updateCV(row.id)
                    }}><i className='fas fa-wrench'/></button></td>
                </tr>
            );
        }
    }

    componentDidMount() {
        this.props.getCVs();
    }

    render() {
        let rows = [];
        let data = this.props.data;
        this.listCVs(rows,data);

        return(
            <HomePageComponent
                uploadCV={this.props.uploadCV}
                getCVs={this.props.getCVs}
                CVs={rows}
                updateCV={this.props.updateCV}
                data={this.state.data}
            />
        );
    }
}