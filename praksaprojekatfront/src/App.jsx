import { useEffect, useState } from "react";

const API = "http://localhost:8080/candidate";
const SKILL_API = "http://localhost:8080/skill";

function App() {
  const [candidates, setCandidates] = useState([]);
  const [skills, setSkills] = useState([]);

  const [showForm, setShowForm] = useState(false);
  const [showSearch, setShowSearch] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);
  const [showSkill, setShowSkill] = useState(false);
  const [showSkillSearch, setShowSkillSearch] = useState(false);
  const [showDeleteSkill, setShowDeleteSkill] = useState(false);
  const [showAddSkillForm, setShowAddSkillForm] = useState(false);

  const [email, setEmail] = useState("");
  const [skillName, setSkillName] = useState("");
  const [skillSearch, setSkillSearch] = useState("");
  const [name, setName] = useState("");
  const [newSkillName, setNewSkillName] = useState("");
  const [deleteCandidateId, setDeleteCandidateId] = useState("");
  const [deleteSkillName, setDeleteSkillName] = useState("");

  const [searchedCandidate, setSearchedCandidate] = useState(null);
  const [candidatesBySkill, setCandidatesBySkill] = useState([]);

  const [addErrorMessage, setAddErrorMessage] = useState("");
  const [searchErrorMessage, setSearchErrorMessage] = useState("");
  const [updateMessage, setUpdateMessage] = useState("");
  const [skillMessage, setSkillMessage] = useState("");
  const [deleteSkillMessage, setDeleteSkillMessage] = useState("");
  const [newSkillMessage, setNewSkillMessage] = useState("");

  useEffect(() => {
    loadCandidates();
    loadSkills();
  }, []);

  const loadCandidates = () => {
    fetch(`${API}/all`)
      .then((res) => {
        if (!res.ok) {
          throw new Error("Greška pri učitavanju kandidata");
        }
        return res.json();
      })
      .then((data) => setCandidates(data))
      .catch((err) => console.log(err.message));
  };

  const loadSkills = () => {
    fetch(`${SKILL_API}/all`)
      .then((res) => {
        if (!res.ok) {
          throw new Error("Greška pri učitavanju skillova");
        }
        return res.json();
      })
      .then((data) => setSkills(data))
      .catch((err) => console.log(err.message));
  };

  const addCandidate = (e) => {
    e.preventDefault();
    setAddErrorMessage("");

    const formData = new FormData(e.target);

    const newCandidate = {
      fullName: formData.get("fullName"),
      email: formData.get("email"),
      contactNumber: formData.get("contactNumber"),
      dateOfBirth: formData.get("dateOfBirth")
    };

    fetch(`${API}/add`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(newCandidate)
    })
      .then(async (res) => {
        const data = await res.json();

        if (!res.ok) {
          throw new Error(
            data.message || Object.values(data).join(", ") || "Greška pri dodavanju kandidata"
          );
        }

        return data;
      })
      .then((data) => {
        setCandidates([...candidates, data]);
        setShowForm(false);
        e.target.reset();
      })
      .catch((err) => {
        setAddErrorMessage(err.message);
      });
  };

  const searchCandidate = async () => {
    setSearchErrorMessage("");
    setSearchedCandidate(null);

    try {
      const res = await fetch(`${API}/name/${encodeURIComponent(name)}`);
      const data = await res.json();

      if (!res.ok) {
        throw new Error(data.message || "Candidate not found");
      }

      setSearchedCandidate(data);
    } catch (err) {
      setSearchErrorMessage(err.message);
    }
  };

  const addSkillToCandidate = (e) => {
    e.preventDefault();
    setSkillMessage("");

    fetch(
      `${API}/addSkill?email=${encodeURIComponent(email)}&skill=${encodeURIComponent(skillName)}`
    )
      .then(async (res) => {
        const data = await res.json();

        if (!res.ok) {
          throw new Error(data.message || "Greška pri dodavanju skilla");
        }

        return data;
      })
      .then(() => {
        setSkillMessage("Skill successfully added");
        setEmail("");
        setSkillName("");
        setShowSkill(false);
        loadCandidates();
      })
      .catch((err) => {
        setSkillMessage(err.message);
      });
  };

  const updateCandidate = (e) => {
    e.preventDefault();
    setUpdateMessage("");

    const formData = new FormData(e.target);

    const updatedCandidate = {
      fullName: formData.get("fullName"),
      email: formData.get("email"),
      contactNumber: formData.get("contactNumber"),
      dateOfBirth: formData.get("dateOfBirth")
    };

    const id = formData.get("id");

    fetch(`${API}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(updatedCandidate)
    })
      .then(async (res) => {
        const text = await res.text();

        if (!res.ok) {
          throw new Error(text || "Greška pri ažuriranju kandidata");
        }

        return text;
      })
      .then((msg) => {
        setUpdateMessage(msg || "Candidate successfully updated");
        e.target.reset();
        setShowUpdate(false);
        loadCandidates();
      })
      .catch((err) => {
        setUpdateMessage(err.message);
      });
  };

  const deleteCandidate = (id) => {
    fetch(`${API}/delete/${id}`, {
      method: "DELETE"
    })
      .then(async (res) => {
        const text = await res.text();

        if (!res.ok) {
          throw new Error(text || "Greška pri brisanju kandidata");
        }

        return text;
      })
      .then(() => {
        loadCandidates();
      })
      .catch((err) => {
        alert(err.message);
      });
  };

  const getCandidatesBySkill = () => {
    fetch(`${API}/withSkill/${encodeURIComponent(skillSearch)}`)
      .then(async (res) => {
        if (!res.ok) {
          throw new Error("Greška pri pretrazi kandidata po skillu");
        }

        return res.json();
      })
      .then((data) => setCandidatesBySkill(data))
      .catch((err) => {
        setCandidatesBySkill([]);
        alert(err.message);
      });
  };

  const deleteSkill = (e) => {
    e.preventDefault();
    setDeleteSkillMessage("");

    fetch(
      `${API}/deleteSkill/${deleteCandidateId}/skills/${encodeURIComponent(deleteSkillName)}`,
      {
        method: "DELETE"
      }
    )
      .then(async (res) => {
        const text = await res.text();

        if (!res.ok) {
          throw new Error(text || "Greška pri brisanju skilla");
        }

        return text;
      })
      .then((msg) => {
        setDeleteSkillMessage(msg || "Skill deleted successfully");
        setDeleteCandidateId("");
        setDeleteSkillName("");
        setShowDeleteSkill(false);
        loadCandidates();
      })
      .catch((err) => {
        setDeleteSkillMessage(err.message);
      });
  };

  const addNewSkill = () => {
    setNewSkillMessage("");

    fetch(`${SKILL_API}/addSkill`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ name: newSkillName })
    })
      .then(async (res) => {
        const text = await res.text();

        if (!res.ok) {
          throw new Error(text || "Greška pri dodavanju skilla");
        }

        return text;
      })
      .then((msg) => {
        setNewSkillMessage(msg || "Skill successfully added");
        setNewSkillName("");
        setShowAddSkillForm(false);
        loadSkills();
      })
      .catch((err) => {
        setNewSkillMessage(err.message);
      });
  };

  return (
    <div style={{ display: "flex", padding: "10px", alignItems: "flex-start", gap: "20px" }}>
      <div style={{ flex: 2, textAlign: "left" }}>
        <h2>List of candidates:</h2>

        <div style={{ width: "60%" }}>
          {candidates.map((c) => (
            <p key={c.id}>
              {c.fullName} - {c.email}
            </p>
          ))}
        </div>

        <br />
        <h2>Functions</h2>

        <button onClick={() => setShowForm(true)}>
          Add Candidate
        </button>

        {showForm && (
          <form onSubmit={addCandidate} style={{ marginTop: "20px" }}>
            <input name="fullName" placeholder="Full name" />
            <br /><br />

            <input name="email" type="email" placeholder="Email" />
            <br /><br />

            <input name="contactNumber" placeholder="Contact number" />
            <br /><br />

            <input name="dateOfBirth" type="date" />
            <br /><br />

            <button type="submit">Save</button>

            {addErrorMessage && (
              <p style={{ color: "red", marginTop: "10px" }}>
                {addErrorMessage}
              </p>
            )}
          </form>
        )}

        <br />
        <button onClick={() => setShowSearch(true)}>
          Search Candidate by name
        </button>

        {showSearch && (
          <div style={{ marginTop: "20px" }}>
            <input value={name} onChange={(e) => setName(e.target.value)} />
            <button onClick={searchCandidate}>Search</button>

            {searchErrorMessage && (
              <p style={{ color: "red" }}>{searchErrorMessage}</p>
            )}

            {searchedCandidate && (
              <div>
                <p>{searchedCandidate.fullName}</p>
                <p>{searchedCandidate.email}</p>
              </div>
            )}
          </div>
        )}

        <br />
        <button onClick={() => setShowUpdate(true)}>
          Update Candidate
        </button>

        {showUpdate && (
          <form onSubmit={updateCandidate} style={{ marginTop: "20px" }}>
            id: <input name="id" />
            <br /><br />

            fullname: <input name="fullName" />
            <br /><br />

            email: <input name="email" />
            <br /><br />

            contact number: <input name="contactNumber" />
            <br /><br />

            date of birth: <input type="date" name="dateOfBirth" />
            <br /><br />

            <button type="submit">Update</button>

            {updateMessage && <p>{updateMessage}</p>}
          </form>
        )}

        <br />
        <button onClick={() => setShowSkill(true)}>
          Add Skill to candidate
        </button>

        {showSkill && (
          <form onSubmit={addSkillToCandidate} style={{ marginTop: "20px" }}>
            candidates email:{" "}
            <input
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <br /><br />

            skill:{" "}
            <input
              value={skillName}
              onChange={(e) => setSkillName(e.target.value)}
            />
            <br /><br />

            <button type="submit">Add Skill</button>
            {skillMessage && <p>{skillMessage}</p>}
          </form>
        )}

        <br />
        <button onClick={() => setShowSkillSearch(true)}>
          Get all candidates with this skill
        </button>

        {showSkillSearch && (
          <div style={{ marginTop: "20px" }}>
            <input
              value={skillSearch}
              onChange={(e) => setSkillSearch(e.target.value)}
            />
            <button onClick={getCandidatesBySkill}>Search</button>

            <div style={{ marginTop: "20px" }}>
              {candidatesBySkill.map((c) => (
                <p key={c.id}>
                  {c.fullName} - {c.email}
                </p>
              ))}
            </div>
          </div>
        )}

        <br />
        <button onClick={() => setShowDeleteSkill(true)}>
          Delete Skill from Candidate
        </button>

        {showDeleteSkill && (
          <form onSubmit={deleteSkill} style={{ marginTop: "20px" }}>
            <input
              placeholder="Candidate ID"
              value={deleteCandidateId}
              onChange={(e) => setDeleteCandidateId(e.target.value)}
            />
            <br /><br />

            <input
              placeholder="Skill name"
              value={deleteSkillName}
              onChange={(e) => setDeleteSkillName(e.target.value)}
            />
            <br /><br />

            <button type="submit">Delete</button>

            {deleteSkillMessage && <p>{deleteSkillMessage}</p>}
          </form>
        )}

        <br /><br /><br />
        <div style={{ textAlign: "left", paddingLeft: "10px" }}>
          <h2>Delete candidate/s</h2>

          {candidates.map((c) => (
            <div key={c.id}>
              {c.fullName} - {c.email}
              <button onClick={() => deleteCandidate(c.id)}>
                Delete
              </button>
              <br /><br />
            </div>
          ))}
        </div>
      </div>

      <div style={{ flex: 1, textAlign: "left" }}>
        <h2>List of skills:</h2>

        <div style={{ marginTop: "20px" }}>
          {skills.map((s) => (
            <p key={s.id}>
              {s.name}
            </p>
          ))}
        </div>

        <button onClick={() => setShowAddSkillForm(true)}>
          Add Skill
        </button>

        {showAddSkillForm && (
          <div style={{ marginTop: "20px" }}>
            Skill name:
            <input
              onChange={(e) => setNewSkillName(e.target.value)}
              value={newSkillName}
            />
            <br /><br />

            <button onClick={addNewSkill}>Save Skill</button>

            {newSkillMessage && <p>{newSkillMessage}</p>}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;