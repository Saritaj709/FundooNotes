package com.bridgelabz.fundonotes.note.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonotes.note.exception.NoteArchievedException;
import com.bridgelabz.fundonotes.note.exception.NoteCreationException;
import com.bridgelabz.fundonotes.note.exception.NoteNotFoundException;
import com.bridgelabz.fundonotes.note.exception.NotePinnedException;
import com.bridgelabz.fundonotes.note.exception.NoteTrashedException;
import com.bridgelabz.fundonotes.note.exception.NullEntryException;
import com.bridgelabz.fundonotes.note.exception.UntrashedException;
import com.bridgelabz.fundonotes.note.exception.UserNotFoundException;
import com.bridgelabz.fundonotes.note.model.CreateDTO;
import com.bridgelabz.fundonotes.note.model.CreateLabelDTO;
import com.bridgelabz.fundonotes.note.model.LabelDTO;
import com.bridgelabz.fundonotes.note.model.NoteDTO;
import com.bridgelabz.fundonotes.note.model.Response;
import com.bridgelabz.fundonotes.note.model.UpdateDTO;
import com.bridgelabz.fundonotes.note.model.ViewDTO;
import com.bridgelabz.fundonotes.note.model.ViewLabelDTO;
import com.bridgelabz.fundonotes.note.services.NoteService;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

	@Autowired
	NoteService noteService;

	// -------------Create A New Note----------------------

	@PostMapping(value = "/create")
	public ResponseEntity<Response> createNote(@RequestHeader(value = "token") String token,
			@RequestBody CreateDTO createDto)
			throws NoteCreationException, NoteNotFoundException, UserNotFoundException {

		noteService.createNote(token, createDto);

		Response response = new Response();

		response.setMessage("Congratulations,your note is successfully created");
		response.setStatus(1);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// ------------------Add A Label--------------------------

	@PostMapping(value = "/createLabel")
	public ResponseEntity<Response> createLabelInsideNote(@RequestHeader(value = "token") String token,
			@RequestBody CreateLabelDTO createLabelDto)
			throws NoteCreationException, NoteNotFoundException, UserNotFoundException, NullEntryException {

		noteService.createLabel(token, createLabelDto);

		Response response = new Response();

		response.setMessage("Congratulations,your label is successfully created");
		response.setStatus(14);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// ------------------View Label---------------------------

	@GetMapping(value = "/view/label")
	public ResponseEntity<List<ViewLabelDTO>> viewLabels()
			throws UserNotFoundException, NoteNotFoundException, NoteTrashedException, NullEntryException {

		noteService.viewLabels();

		return new ResponseEntity<>(noteService.viewLabels(), HttpStatus.OK);
	}

	// ---------------Add Label To Notes-----------------------
	@PostMapping(value = "/addLabel/{noteId}")
	public ResponseEntity<Response> addLabelToNotes(@RequestHeader(value = "token") String token,
			@RequestParam(value = "labelId") String labelName, @PathVariable(value = "noteId") String noteId)
			throws NoteNotFoundException, UserNotFoundException, NoteTrashedException {

		noteService.addLabel(token, labelName, noteId);

		Response response = new Response();

		response.setMessage("Congratulations,your label is successfully added");
		response.setStatus(15);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//---------------Edit Or Delete A Label-----------------
	@PostMapping(value = "/editOrDeleteLabel")
	public ResponseEntity<Response> editOrDeleteLabel(@RequestHeader(value = "token") String token,
			@RequestBody LabelDTO labelDto,@RequestParam(value="choice,edit/delete")String choice)
			throws Exception {

		noteService.editOrRemoveLabel(token,labelDto,choice);

		Response response = new Response();

		response.setMessage("Congratulations,your label is successfully updated");
		response.setStatus(16);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	// ------------Update An Existing Note-------------------

	@PutMapping(value = "/update")
	public ResponseEntity<Response> updateNote(@RequestHeader(value = "token") String token,
			@RequestBody UpdateDTO updateDto)
			throws NoteNotFoundException, UserNotFoundException, NoteTrashedException {

		noteService.updateNote(token, updateDto);

		Response response = new Response();

		response.setMessage("Congratulations,your details are successfully updated");
		response.setStatus(2);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*// ------------Move An Existing Note To Trash---------------

	@PostMapping(value = "/delete/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable String noteId,
			@RequestHeader(value = "userId") String token, HttpServletRequest req)
			throws NoteNotFoundException, UserNotFoundException, UntrashedException, NoteTrashedException {

		// String userId=(String) req.getAttribute("token");
		noteService.trashNote(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations, your note is successfully moved to trash");
		response.setStatus(3);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}*/

	// --------------------View trashed Notes--------------------

	@GetMapping("/view/trashed")
	public ResponseEntity<List<ViewDTO>> viewTrashedNotes()
			throws UserNotFoundException, NoteNotFoundException, NoteTrashedException, NullEntryException {

		noteService.viewTrashed();

		return new ResponseEntity<>(noteService.viewTrashed(), HttpStatus.OK);
	}

	/*// --------------------Restore A Note From Trash------------

	@PostMapping(value = "/restore/{noteId}")
	public ResponseEntity<Response> restoreNote(@RequestHeader(value = "token") String token,
			@PathVariable String noteId)
			throws NoteNotFoundException, UserNotFoundException, UntrashedException, NoteTrashedException {

		noteService.restoreNote(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations, your note is successfully restored");
		response.setStatus(10);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}*/
	
	//------------------Move To Trash Or Restore A Note-----------------------
	@PostMapping(value = "/delete/restore/{noteId}")
	public ResponseEntity<Response> deleteOrRestoreNote(@RequestHeader(value = "token") String token,
			@PathVariable String noteId,@RequestParam(value="choice,delete/restore")String choice)
			throws NoteNotFoundException, UserNotFoundException, UntrashedException, NoteTrashedException {

		noteService.deleteOrRestoreNote(token, noteId,choice);

		Response response = new Response();

		response.setMessage("Congratulations, your note is successfully restored/trashed");
		response.setStatus(117);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ----------Delete An Existing Note From Trash--------------

	@DeleteMapping(value = "/deleteForever/{noteId}")
	public ResponseEntity<Response> deleteNoteForever(@PathVariable String noteId,
			@RequestHeader(value = "token") String token)
			throws NoteNotFoundException, UserNotFoundException, UntrashedException, NoteTrashedException {

		noteService.deleteNoteForever(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations,your details are successfully deleted");
		response.setStatus(4);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ---------------To Archieve Notes----------------------------

	@PostMapping(value = "/archieve/{noteId}")
	public ResponseEntity<Response> archieveNote(@PathVariable String noteId,
			@RequestHeader(value = "token") String token)
			throws NoteNotFoundException, UserNotFoundException, NoteTrashedException, NoteArchievedException {

		noteService.archieveNote(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations, your note is successfully moved to trash");
		response.setStatus(11);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// --------------View Archieved Notes--------------------------------

	@GetMapping(value = "/view/archieved")
	public ResponseEntity<List<ViewDTO>> viewArchievedNotes() throws NullEntryException {

		noteService.viewArchieved();

		return new ResponseEntity<>(noteService.viewArchieved(), HttpStatus.OK);
	}
	// -------------------To Pin Notes---------------------------------

	@PostMapping(value = "/pin/{noteId}")
	public ResponseEntity<Response> pinNote(@PathVariable String noteId, @RequestHeader(value = "token") String token)
			throws NoteNotFoundException, UserNotFoundException, NotePinnedException, NoteTrashedException {

		noteService.pinNote(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations, your note is successfully moved to trash");
		response.setStatus(12);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ------------------View Pinned Notes----------------------------

	@GetMapping(value = "/view/pinned")
	public ResponseEntity<List<ViewDTO>> viewPinnedNotes() throws NullEntryException {

		noteService.viewPinned();

		return new ResponseEntity<>(noteService.viewPinned(), HttpStatus.OK);
	}

	// ------------Read All Existing Notes--------------------------

	@GetMapping("/readAll")
	public ResponseEntity<List<ViewDTO>> readNote()
			throws NullEntryException, NoteNotFoundException, NoteCreationException, UserNotFoundException {

		noteService.readNotes();

		return new ResponseEntity<>(noteService.readNotes(), HttpStatus.OK);
	}

	// ----------------Read Entire Details Of All The Notes------------

	@GetMapping("/readAllNotes")
	public ResponseEntity<List<NoteDTO>> readAllNotes()
			throws NullEntryException, NoteNotFoundException, NoteCreationException, UserNotFoundException {

		noteService.readAllNotes();

		return new ResponseEntity<>(noteService.readAllNotes(), HttpStatus.OK);
	}

	// ----------Read A Particular Note-------------------------------

	@PostMapping("/readOneNote/{noteId}")
	public ResponseEntity<ViewDTO> readParticularNote(@RequestHeader(value = "token") String token,
			@PathVariable("noteId") String noteId)
			throws UserNotFoundException, NoteNotFoundException, NoteTrashedException {

		noteService.findNoteById(token, noteId);

		return new ResponseEntity<>(noteService.findNoteById(token, noteId), HttpStatus.OK);
	}

	// ---------Add A Reminder To A Particular Note---------------------

	@RequestMapping(value = "/addReminder/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<Response> addNoteReminder(@RequestHeader(value = "token") String token,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @PathVariable String noteId)
			throws NoteCreationException, UserNotFoundException, NoteNotFoundException, NoteTrashedException {

		noteService.addReminder(token, date, noteId);

		Response response = new Response();

		response.setMessage("Congratulations,your reminder is successfully set!!");
		response.setStatus(7);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// -------Delete Reminder From A Note---------------------------------

	@RequestMapping(value = "/deleteReminder/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<Response> deleteNoteReminder(@RequestHeader(value = "token") String token,
			@PathVariable String noteId)
			throws NullEntryException, UserNotFoundException, NoteNotFoundException, NoteTrashedException {

		noteService.deleteReminder(token, noteId);

		Response response = new Response();

		response.setMessage("Congratulations,your reminder is successfully removed!!");
		response.setStatus(9);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
